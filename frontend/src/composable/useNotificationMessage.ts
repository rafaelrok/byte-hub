import { ref, Ref } from 'vue'
import { api } from 'src/services'

export function useNotificationMessage(targetProfileId: number) {
  const error: Ref<any | null> = ref(null)

  async function getNotificationMessage() {
    try {
      const response =
        await api.notificationMessageFollow.getNotificationMessageFollow(
          targetProfileId,
        )
      return response.data
    } catch (e) {
      error.value = e
    }
  }

  async function getTotalNotificationMessage() {
    try {
      const response =
        await api.notificationMessageFollow.getTotalNotificationMessageFollow(
          targetProfileId,
        )
      return response.data
    } catch (e) {
      error.value = e
    }
  }

  async function getNotificationMessageFollowReading() {
    try {
      const response =
        await api.notificationMessageFollow.getNotificationMessageFollowReading(
          targetProfileId,
        )
      return response.data
    } catch (e) {
      error.value = e
    }
  }

  async function updateFollowReading(notificationId: number) {
    try {
      const response =
        await api.notificationMessageFollow.updateNotificationMessageFollowReading(
          notificationId,
        )
      return response.data
    } catch (e) {
      error.value = e
    }
  }

  return {
    getNotificationMessage,
    getTotalNotificationMessage,
    getNotificationMessageFollowReading,
    updateFollowReading,
    error,
  }
}
