import { ref } from 'vue'
import { api } from 'src/services'

export function useSearch() {
  const search = ref<string[]>([])

  async function fetchSearch(query: string): Promise<void> {
    search.value = []
    const { data } = await api.articles.getArticles()
    search.value = data.articles
      .filter((article) =>
        article.title.toLowerCase().includes(query.toLowerCase()),
      )
      .map((article) => article.title)
  }

  return {
    fetchSearch,
    search,
  }
}
