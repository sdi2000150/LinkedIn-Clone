/* JavaScript Cheat Sheet */

// Singleline Comment
/* Multiline
Comment */

/////////////////////////////////////////////////////////////////////
// Ways of printing into browser's developer console
console.log("Alert, from external js file");
console.error("This is an error");
console.warn("This is a warning");

/////////////////////////////////////////////////////////////////////
// Variables: 
//var: global-we avoid it, 
//let: classic, 
//const: like let, but constant

let score = 30;
score = 31;
console.log(score);

/////////////////////////////////////////////////////////////////////
// Strings, Numbers, Boolean, null, undefined:

const name = "John";
const age = 30;
const rating = 4.5;
const isCool = true;
const x = null; //defines as object, null
const y = undefined;
let z; //undefined too

console.log(typeof name);
console.log(typeof age);
console.log(typeof rating);
console.log(typeof isCool);
console.log(typeof x);
console.log(typeof y);
console.log(typeof z);

/////////////////////////////////////////////////////////////////////
// Concatenation (old way)
console.log("My name is " + name + " and I am " + age);

// Template String (new way)
//console.log(`My name is ${name} and I am ${age}`);
const hello = `My name is ${name} and I am ${age}`;
console.log(hello);

/////////////////////////////////////////////////////////////////////
// String Properties-Methods
const s = "tech, pc, it, code";

console.log(s.length); //properties have no ()

console.log(s.toUpperCase()); //methods have ()

console.log(s.substring(0, 5).toUpperCase());   //mix methods

console.log(s.split(", ")); //using delimeter , for tokenizing the string

/////////////////////////////////////////////////////////////////////
// Arrays - variables that hold multiple values
// 1st way: using the Constructor
const numbers = new Array(1, 2, 3, 4, 5);
console.log(numbers);
// 2nd way: simple creation
const fruits = ["apples", "oranges", "pears"];
console.log(fruits);
console.log(fruits[1]);

// fruits = []; //cannot do this because the array is defined as const
fruits[3] = "grapes";   //but i can manipulate/add/remove its elements
console.log(fruits);

fruits.push("mangos");  //push to the end
console.log(fruits);
fruits.unshift("strawberries");  //add to the beggining
console.log(fruits);
fruits.pop();
console.log(fruits);    //pop(remove) from the end

console.log(Array.isArray(fruits)); //check if fruits is an array

console.log(fruits.indexOf("oranges")); //check at which position in the array an element

/////////////////////////////////////////////////////////////////////
// Object Literals (like structs)
const person = {
    firstName: "Teo",
    lastName: "Mor",
    age: 30,
    hobbies: ["music", "movies", "coding"],
    address: {
        street: "Dimokratias 36",
        city: "Athens",
        state: "Attica"
    }
}

console.log(person);
console.log(person.firstName, person.lastName);
console.log(person.hobbies[1]);
console.log(person.address.city);

const { firstName, lastName, address: { city } } = person; //pull things out of an object (without destroying struct in any way)
console.log(firstName, lastName, city);   //local variables
console.log(person.firstName, person.lastName, person.address.city); //still inside safe

person.email = "teomor@gmail.com";  //add another/new field into the struct
console.log(person);

/////////////////////////////////////////////////////////////////////
// Arrays of Objects
const todos = [
    {
        id: 1,
        text: "Take out trash",
        isCompleted: true
    },
    {
        id: 2,
        text: "Meeting with boss",
        isCompleted: true
    },
    {
        id: 3,
        text: "Doctor appt",
        isCompleted: false
    },
];

console.log(todos);
console.log(todos[1].text);

/////////////////////////////////////////////////////////////////////
/* JSON (JavaScript Object Notation) is a lightweight data interchange format 
that is easy for humans to read and write, and easy for machines to parse 
and generate. It is commonly used to transmit data between a server 
and a web application as text. */

// Convert object into JSON
const todoJSON = JSON.stringify(todos);
console.log(todoJSON);

/////////////////////////////////////////////////////////////////////
// For loop
for(let i = 0; i < 10; i++) {
    console.log(`For loop number: ${i}`);
}

// While loop
let i = 0;
while(i < 10) {
    console.log(`While loop number: ${i}`);
    i++;
}

/////////////////////////////////////////////////////////////////////
// Simple traverse/loop through array:
//1st way
for(let i = 0; i < todos.length; i++) {
    console.log(todos[i]);
}
//2nd way
for(let item of todos) {    //item can whatever name we want
    console.log(item);
}

/////////////////////////////////////////////////////////////////////
// High order array methods of iteration of arrays:
// forEach method:
todos.forEach(function(item) {
    console.log(item.text);
})
// map method:
const todoText = todos.map(function(item) {
    return item.text;
})
console.log(todoText);
// filter method:
const todoCompleted = todos.filter(function(item) {
    return item.isCompleted == true;
})
console.log(todoCompleted);
// mix of filter and map methods:
const todoCompleted2 = todos.filter(function(item) {
    return item.isCompleted == true;
}).map(function(item) {
    return item.text;
})
console.log(todoCompleted2);

/////////////////////////////////////////////////////////////////////
// Conditionals (if statement)

const x1 = "10";    //same
// const x1 = 10;   //same 
if (x1 == 10) {      //when we use == (because == doesnt care about datatypes)
    console.log(`x1 is 10`);
}

const y1 = "10";    //not same
// const y1 = 10;   //same
if (y1 === 10) {      //when we use === (because === matches the datatypes too)
    console.log(`y1 is 10`);
}
//so, it is safe to ALWAYS USE === IN CONDITIONALS

let x2 = 6;
if (x2 === 10) {
    console.log(`x2 is 10`);
} else if (x2 > 10) {
    console.log(`x2 is greater than 10`);
} else {
    console.log(`x2 is less than 10`);
}
let y2 = 11;
if (x2 > 5 || y2 > 10) {    //OR
    console.log(`x2 is more than 5 or y2 is more than 10`);
}
if (x2 > 5 && y2 > 10) {    //AND
    console.log(`x2 is more than 5 and y2 is more than 10`);
}

/////////////////////////////////////////////////////////////////////
// Ternary operator (shortened if)
const x3 = 10;

const color = x3 > 10 ? "red" : "blue";

console.log(color);

/////////////////////////////////////////////////////////////////////
// Switch statement
switch(color) {
    case "red":
        console.log("color is red");
        break;
    case "blue":
        console.log("color is blue");
        break;
    default:
        console.log("color is not red or blue");
        break;
}

/////////////////////////////////////////////////////////////////////
// Functions:

// Simple function
function addNums(num1 = 1, num2 = 1) {  //just the declaration/definition of the function
    console.log(num1 + num2);
    return num1 + num2;
}

addNums(5,4);   //here we actually call the function
console.log(addNums(5,4));  //same, using retun value to be printed
addNums();   //prints NaN if parameters of function are not set to default value, otherwise default values of parameters are used

// Arrow function
const addNumsArrow = (num1 = 1, num2 = 1) => {  //just the declaration/definition of the function
    console.log(num1 + num2);
}
// const addNumsArrow = (num1 = 1, num2 = 1) => num1 + num2; //same, with return but skipping return
addNumsArrow(5,5);   //here we actually call the function

/////////////////////////////////////////////////////////////////////
// OOP Programming ES5 (old) (pre-classes)

// Constructor function
function Person(firstName, lastName, dob) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = new Date(dob);
    // this.getBirthYear = function() {
    //     return this.dob.getFullYear();
    // }
    // this.getFullName = function() {
    //     return `${this.firstName} ${this.lastName}`
    // }
}
// define object methods as prototypes
Person.prototype.getBirthYear = function() {
    return this.dob.getFullYear();
}
Person.prototype.getFullName = function() {
    return `${this.firstName} ${this.lastName}`
}

// Instantiate object
const person1 = new Person("John", "Doe", "4-3-1980");
const person2 = new Person("Mary", "Smith", "3-6-1970");

// Access object
console.log(person1);
console.log(person2);

console.log(person2.dob);
console.log(person1.getBirthYear());    //one way
console.log(person2.dob.getFullYear()); //another way
console.log(person1.getFullName());
console.log(person1);

/////////////////////////////////////////////////////////////////////
// OOP Programming ES6 (2015) (with classes)

// Class definition
class PersonClass {
    constructor(firstName, lastName, dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = new Date(dob);
    }

    getBirthYear() {
        return this.dob.getFullYear();
    }

    getFullName() {
        return `${this.firstName} ${this.lastName}`
    }
}

// Instantiate object
const personclass1 = new PersonClass("Teo", "Mor", "2-8-2002");
const personclass2 = new PersonClass("Brad", "Pitt", "15-7-1965");

// Access object
console.log(personclass1);
console.log(personclass2);

console.log(personclass2.dob);
console.log(personclass1.getBirthYear());    //one way
console.log(personclass2.dob.getFullYear()); //another way
console.log(personclass1.getFullName());
console.log(personclass1);

/////////////////////////////////////////////////////////////////////
// window object (the parent object of the browser)
console.log(window);
// window.alert(1);
// alert(1); //same as above

/////////////////////////////////////////////////////////////////////
/* The DOM (Document Object Model) in JavaScript is a programming interface 
for web documents (HTMLs). It represents the structure of a document as a tree of nodes, 
allowing programs and scripts to dynamically access and update the content, 
structure, and style of a document. */

/////////////////////////////////////////////////////////////////////
// DOM selection (selecting things from DOM, select HTML elements and work with them):

// Single element selectors
const form = document.getElementById("my-form"); //one way
console.log(form);
console.log(document.querySelector(".container"));  //another way (best one)
console.log(document.querySelector("h1"));

// Multiple elements selectors
console.log(document.querySelectorAll(".item"));    //one way (nodelist) (best one)
console.log(document.getElementsByClassName("item")); //another way (HTML collection)
console.log(document.getElementsByTagName("li"));   //same as above

// iterate through item of list in HTML
const items = document.querySelectorAll(".item");
items.forEach((item) => console.log(item));

/////////////////////////////////////////////////////////////////////
// Manipulating the DOM (changing things in UI/interaction HTML):

//changing HTML things
const ul = document.querySelector(".items");
// ul.remove();    //remove list from UI HTML
ul.lastElementChild.remove();   //remove specific element from list
ul.firstElementChild.textContent = "hello1"; //change name of first element of list
ul.children[1].innerText = "Brad";
ul.lastElementChild.innerHTML = "<h1>hello2</h1>"

//change styling (CSS things)
const btn = document.querySelector(".btn");
btn.style.background = "red";

//activate events on clicks etc...
btn.addEventListener("mouseover", function(e) { //or "click" or "mouseout" or ... (a lot of events available)
    e.preventDefault(); //to prevent the default submit button function
    console.log("click");   //show click on console
    console.log(e.target.className); //show an attribute of btn in console
    document.querySelector("#my-form").style.background = "#ccc";
    document.querySelector("body").classList.add("bg-dark");
    document.querySelector(".items").lastElementChild.innerHTML = "<h1>Hello3</h1>";
});

/////////////////////////////////////////////////////////////////////
// Create it functional
const myForm = document.querySelector("#my-form");
const nameInput = document.querySelector("#name");
const emailInput = document.querySelector("#email");
const msg = document.querySelector(".msg");
const userList = document.querySelector("#users");

myForm.addEventListener("submit", onSubmit);

function onSubmit(e) {
    e.preventDefault();
    console.log(nameInput.value);

    if (nameInput.value === "" || emailInput === "") {
        msg.classList.add("error");
        msg.innerHTML = "Please enter both fields";

        setTimeout(function() {
            msg.remove();
        }, 3000);
    } else {
        console.log("success");

        const li = document.createElement("li");
        li.appendChild(document.createTextNode(`${nameInput.value} : ${emailInput.value}`));
        
        userList.appendChild(li);

        //clear the fields
        nameInput.value = "";
        emailInput.value = "";
    }

}