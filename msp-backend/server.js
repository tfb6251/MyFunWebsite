const express = require('express');
const cors = require('cors');
const { SerialPort } = require('serialport');

const app = express();
app.use(cors());

let latestDistance = null;

// Open serial port (COM4, 9600 baud for example)
const port = new SerialPort({
    path: 'COM4',
    baudRate: 9600
});

// When data arrives from MSP432
port.on('data', (data) => {
    latestDistance = data.toString().trim();
    console.log("Distance:", latestDistance);
});

// REST endpoint Angular will call
app.get('/distance', (req, res) => {
    res.json({ distance: latestDistance });
});

const server = app.listen(3000, () => {
    console.log("Backend running on http://localhost:3000");
});