<template>
  <div>
    <h2>Order</h2>

    <nav class="order-nav">
      <router-link to="/order/menu">Food Menu</router-link>
      <router-link to="/order/cart">Cart</router-link>
    </nav>

    <router-view />
  </div>
</template>

<script setup>
</script>

<style scoped>
.order-nav {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
  
}

.order-nav a {
  text-decoration: none;
  color: #2980b9;
}
</style>