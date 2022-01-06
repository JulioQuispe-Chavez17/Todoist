const express = require('express');
const router = express.Router();
const { getAll, create, update, getOne, destroy } = require('./controller');
const { validateCreate, validateOne, validateUpdate } = require('./validation')

router.get('/todos', getAll);
router.post('/todos', validateCreate ,create);
router.get('/todos/:id', validateOne , getOne)
router.put('/todos/:id', validateUpdate, update)
router.delete('/todos/:id', validateOne, destroy)
module.exports = router;