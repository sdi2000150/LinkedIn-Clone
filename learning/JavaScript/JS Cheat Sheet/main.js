/* JavaScript Cheat Sheet */

// Singleline comment
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
// Conditionals (if statements)

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
