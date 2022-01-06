var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/todos', function(req, res, next) {
  res.json({ message: 'hello api node' });
});

module.exports = router;
