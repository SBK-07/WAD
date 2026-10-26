<template>
  <div class="app">
    <nav class="navbar">
      <router-link to="/">Home</router-link>
      <router-link to="/order">Order</router-link>
      <router-link to="/about">About</router-link>
    </nav>

    <router-view />
  </div>
</template>

<script setup>
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.app {
  max-width: 700px;
  margin: 0 auto;
  padding: 15px;
  min-height: 100vh;
  font-family: Arial, sans-serif;
}

.navbar {
  display: flex;
  gap: 15px;
  padding: 15px 35px;
  margin-bottom: 15px;
  border-bottom: 1px solid #ccc;
  background-color: black;
  color:white;

}

.navbar a {
  text-decoration: none;
  color: white;
  font-weight: bold;
  border-color:grey;
}

.navbar a :hover{
  background-color: grey;
}
.navbar a.router-link-exact-active {
  color: #e67e22;
}
</style>