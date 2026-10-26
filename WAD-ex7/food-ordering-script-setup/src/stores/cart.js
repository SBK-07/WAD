import { defineStore } from 'pinia'

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: []
  }),

  getters: {
    totalItems: (state) =>
      state.items.reduce((sum, item) => sum + item.quantity, 0),

    totalBill: (state) =>
      state.items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      )
  },

  actions: {
    addToCart(food) {
      const existing = this.items.find(item => item.id === food.id)

      if (existing) {
        existing.quantity++
      } else {
        this.items.push({ ...food, quantity: 1 })
      }
    },

    increaseQuantity(id) {
      const item = this.items.find(item => item.id === id)
      if (item) item.quantity++
    },

    decreaseQuantity(id) {
      const item = this.items.find(item => item.id === id)

      if (item) {
        item.quantity--

        if (item.quantity <= 0) {
          this.removeFromCart(id)
        }
      }
    },

    removeFromCart(id) {
      this.items = this.items.filter(item => item.id !== id)
    },

    clearCart() {
      this.items = []
    }
  }
})