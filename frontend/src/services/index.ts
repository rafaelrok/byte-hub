import { CONFIG } from 'src/config'
import type { GenericErrorModel, HttpResponse } from 'src/services/api'
import { Api, ContentType } from 'src/services/api'

export const limit = 10

// TODO: Implementacao deprecated
// export const api = new Api({
//   baseUrl: `${CONFIG.API_HOST}`,
//   securityWorker: (token) =>
//     token ? { headers: { Authorization: `Bearer ${String(token)}` } } : {},
//   baseApiParams: {
//     headers: {
//       'content-type': ContentType.Json,
//     },
//     format: 'json',
//   },
// })

// TODO: New implementation, using localStorage to get the token from the user
// TODO: Upcoming updates will use the context to get the token and refresh it when necessary, and  refresh token
export const api = new Api({
  baseUrl: `${CONFIG.API_HOST}`,
  securityWorker: () => {
    const userStr = localStorage.getItem('user')
    let token = null
    if (userStr) {
      try {
        const userObj = JSON.parse(userStr)
        token = userObj.token
      } catch (e) {
        console.error('Error parsing localStorage user:', e)
      }
    }
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
  },
  baseApiParams: {
    headers: {
      'content-type': ContentType.Json,
    },
    format: 'json',
  },
})

export function pageToOffset(
  page: number = 1,
  localLimit = limit,
): { limit: number; offset: number } {
  const offset = (page - 1) * localLimit
  return { limit: localLimit, offset }
}

export function isFetchError<E = GenericErrorModel>(
  e: unknown,
): e is HttpResponse<unknown, E> {
  return e instanceof Object && 'error' in e
}
