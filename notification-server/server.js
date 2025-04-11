import express from 'express';
import fs from 'fs';
import { exec } from 'child_process';

const app = express();
const port = 3000;
const outputPath = '/tmp/notification.json';

app.use(express.json());

app.post('/send-notification', (req, res) => {
  const { title, subtitle, sound_url } = req.body;

  if (!title || !subtitle || !sound_url) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  const notificationPayload = {
    "Simulator Target Bundle": "com.sailthru.SoundURLtoPush",
    aps: {
      alert: {
        title,
        subtitle
      },
      sound_url
    }
  };

  fs.writeFile(outputPath, JSON.stringify(notificationPayload, null, 2), (err) => {
    if (err) {
      return res.status(500).json({ error: 'Failed to write notification file' });
    }
    return res.json({ message: 'Notification created and APNs script triggered'});
  });
});

app.listen(port, () => {
  console.log(`Notification service listening on http://localhost:${port}`);
});
