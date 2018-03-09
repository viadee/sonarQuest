var express = require('express');
var router = express.Router();
const fs = require('fs');
/* GET home page. */
router.get('/projects', function(req, res, next) {
  res.send(JSON.parse(fs.readFileSync("projectRessource.json")));
});

router.get('/issues/search', function(req, res, next) {
  res.send(JSON.parse(fs.readFileSync("issueRessource.json")));
});

module.exports = router;
