const express = require('express')
var http = require('http')
var app = express()
var server = http.createServer(app)
var port = process.env.PORT || 3000;
var io = require('socket.io').listen(server)
app.get('/',(req,res)=>{
    res.send("Chat server is running on the port 3000")
})  

io.on('connection',(socket)=>{
    console.log('user connected')

    socket.on('emergency',(data) =>{
        console.log(data)
        socket.broadcast.emit('emergency',data)
    })

    socket.on('messagesent',(t,m)=>{
        let message = {t,m}
        io.emit('messageDetection',message)
    })


})  


server.listen(port,()=>{
    console.log("Chat server is running on the port 3000")
})  
