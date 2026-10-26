<template>
  <div>
    <h3>Food Menu</h3>

    <div
      class="food-item"
      v-for="food in foods"
      :key="food.id"
    >
      <span>{{ food.emoji }} {{ food.name }}</span>
      <span>Rs.{{ food.price }}</span>

      <button @click="addToCart(food)">
        Add to Cart
      </button>
    </div>
  </div>
</template>

<script setup>
import { useCartStore } from '../stores/cart'

const cart = useCartStore()

const foods = [
  { id: 1, name: 'Pizza', price: 250, emoji: '🍕' },
  { id: 2, name: 'Burger', price: 150, emoji: '🍔' },
  { id: 3, name: 'Pasta', price: 200, emoji: '🍝' },
  { id: 4, name: 'Coke', price: 80, emoji: '🥤' }
]

function addToCart(food) {
  cart.addToCart(food)
  alert(`${food.name} added to cart!`)
}
</script>

<style scoped>
.food-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.food-item span:first-child {
  flex: 1;
}

button {
  padding: 6px 10px;
  background-color: #27ae60;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: blue;
  color: whitesmoke;
  cursor:pointer;
}
</style>