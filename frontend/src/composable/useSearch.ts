import { ref } from 'vue'
import { api } from 'src/services'

export function useSearch() {
  const search = ref<string[]>([])

  async function fetchSearch(): Promise<void> {
    search.value = []
    search.value = await api.articles
      .getArticles()
      .then(({ data }) => data.articles.map((article) => article.title))
  }

  return {
    fetchSearch,
    search,
  }
}
