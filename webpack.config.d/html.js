const path = require('path');
const fs = require('fs');

if (process.env.TRAVIS) {
    fs.writeFileSync(
        path.join(__dirname, 'index.html'),
        fs.readFileSync(
            path.join(__dirname, '..', 'src', 'main', 'web', 'index.html'),
            'utf8'
        ).replace('countrygame.bundle.js', 'bundle/countrygame.bundle.js')
    );
}
