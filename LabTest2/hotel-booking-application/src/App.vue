<script setup>

import {ref, onMounted} from 'vue'
import api from "./services/api.js"


const rooms = ref([])
const error = ref('')
const loading = ref(true)
const customerName = ref('')
const roomId = ref('')
const checkIn = ref('')
const checkOut = ref('')
const amount = ref('')

const fetchRooms = async() => {
  try{
    const response = await api.get('/room')
    rooms.value = response.data

  }
  catch(err){
    error.value = "error in fetch"
  }
  finally{
    loading.value = false
  }


 
}
const bookRoom = async () => {
  const booking = {
    customerName: customerName.value,
    roomId: roomId.value,
    checkIn: checkIn.value,
    checkOut: checkOut.value,
    amount: amount.value
  }

  try {
    const response = await api.post('/booking/book', booking)
    console.log('Booking added:', response.data)
    await fetchRooms()
  } catch (err) {
    console.error('Error adding booking:', err)
  }
}

onMounted(fetchRooms)

</script>

<template>
  <div>

  <p v-if="loading">
    Loading....
  </p>

  <p v-else-if="error">
    {{ error }}
  </p>

  <template v-else>

     <div>
    <h1>Room Booking Form</h1>

    <form>
      <label>Customer Name:</label>
      <input
        type="text"
        v-model="customerName"
        placeholder="Enter customer name"
      />

      <br /><br />

      <label>Room ID:</label>
      <input
        type="text"
        v-model="roomId"
        placeholder="Enter room ID"
      />

      <br /><br />

      <label>Check-in Date:</label>
      <input
        type="date"
        v-model="checkIn"
      />

      <br /><br />

      <label>Check-out Date:</label>
      <input
        type="date"
        v-model="checkOut"
      />

      <br /><br />

      <label>Amount:</label>
      <input
        type="number"
        v-model="amount"
        placeholder="Enter amount"
      />

      <br /><br />

      <button type="button" @click="bookRoom">Book Room</button>
    </form>
  </div>

    <h1> Rooms available</h1>
    
    <div v-for="room in rooms"
    :key = "room.id" class="cards">
    <p>Room Type: {{ room.roomType }}</p>
    <p>Status: {{ room.status ? 'Available' : 'Unavailable' }}</p>
    <p>Price: ₹{{ room.price }}</p>
    <p>Capacity: {{ room.capacity }}</p>
    

    </div>
   </template>
   </div>
</template>

<style scoped>

.cards {
  border: 2px solid black;
  padding: 5px;
  margin: 5px;

}

</style>
