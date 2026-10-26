<template>
  <div>
    <h3> Cart ({{ cart.totalItems }} items)</h3>

    <p v-if="cart.items.length === 0">
      Your cart is empty.
    </p>

    <div v-else>
      <div
        class="cart-item"
        v-for="item in cart.items"
        :key="item.id"
      >
        <span>{{ item.emoji }} {{ item.name }}</span>

        <button @click="cart.decreaseQuantity(item.id)">-</button>
        <span>{{ item.quantity }}</span>
        <button @click="cart.increaseQuantity(item.id)">+</button>

        <span>₹{{ item.price * item.quantity }}</span>

        <button @click="cart.removeFromCart(item.id)">
          Remove
        </button>
      </div>

      <hr />

      <p>
        <strong>Total: Rs.{{ cart.totalBill }}</strong>
      </p>

      <button @click="cart.clearCart()">Clear Cart</button>
      <button @click="placeOrder">Place Order</button>

      <p v-if="orderMessage" class="success">
        {{ orderMessage }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useCartStore } from '../stores/cart'

const cart = useCartStore()
const orderMessage = ref('')

function placeOrder() {
  if (cart.items.length === 0) {
    orderMessage.value = 'Please add items before placing an order.'
    return
  }

  orderMessage.value = 'Order placed successfully!'
  cart.clearCart()
}
</script>

<style scoped>
.cart-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.cart-item span:first-child {
  flex: 1;
}

button {
  padding: 4px 8px;
  margin-right: 5px;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  border-radius: 4px;
  cursor: pointer;
}

.success {
  margin-top: 15px;
  font-weight: bold;
  color: #27ae60;
}

button:hover {
  background-color: black;
  color:white;
}
</style>