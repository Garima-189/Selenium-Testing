var express = require("express");
var app = express();
var port = 3000;

 var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(__dirname));

 var mongoose = require("mongoose");
mongoose.Promise = global.Promise;
mongoose.connect("mongodb://localhost:27017/covid19-registry");

var infoSchema = new mongoose.Schema({
fname: String,
lname: String,
dob: Date,
patient_street: String,
patient_plotNo: String,
patient_city: String,
patient_state: String,
source: String,
street: String,
plotNo: String,
city: String,
state: String,
director: String,
testingMethod: String,
specimen: String,
performed_date: Date,
results: String


});

var User = mongoose.model("User", infoSchema);



app.get("/", (req, res) => {
 res.sendFile(__dirname + "/index.html");
});

app.post("/addinfo", (req, res) => {
var myData = new User(req.body);


myData.save()
.then(item => {
res.set('title','Passed');
res.send('<h1> Information saved to database. </h1>');
})
.catch(err => {
	res.set('title','Failed');
res.status(400).send('<h1>Unable to save to database</h1>');

});
});
 
app.listen(port, () => {
 console.log("Server listening on port " + port);
});


