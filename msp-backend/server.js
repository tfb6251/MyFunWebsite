const express = require('express');
const cors = require('cors');
const { SerialPort } = require('serialport');

const app = express();
app.use(cors());

let latestDistance = {
    ft: 0,
    m: 100,
    in: 0,
    cm: 0,
    us: 0
  };; // default value if hardware not connected

let port;

try {
    port = new SerialPort({
        path: 'COM4',
        baudRate: 9600,
        autoOpen: true
    });

    let buffer = '';

    port.on('data', (data) => {
        buffer += data.toString();
    
        // Process only when a full line arrives
        if (buffer.includes('\n')) {
        
            const lines = buffer.split('\n');
        
            // Take only complete lines; leave remainder in buffer
            buffer = lines.pop();
        
            for (let line of lines) {
                const text = line.trim();
                const nums = text.match(/[-+]?[0-9]*\.?[0-9]+/g);
            
                if (nums && nums.length >= 5) {
                    latestDistance = {
                        ft: parseFloat(nums[0]),
                        m: parseFloat(nums[1]),
                        in: parseFloat(nums[2]),
                        cm: parseFloat(nums[3]),
                        us: parseInt(nums[4])
                    };
                    console.log("Parsed:", latestDistance);
                }
            }
        }
    });


    port.on('error', (err) => {
        console.warn('Serial port error:', err.message);
    });
} catch (err) {
    console.warn('Could not open COM port, hardware may be disconnected:', err.message);
}

// REST endpoint Angular will call
app.get('/distance', (req, res) => {
    res.json(latestDistance);
});

const server = app.listen(3000, () => {
    console.log("Backend running on http://localhost:3000");
});