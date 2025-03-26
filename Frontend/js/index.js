// a = 1;
// b ='2';
// d = 3

// c = +a + +b + +d;

// console.log(c);
// console.log(c*2);

// document.getElementById("result").innerHTML = c;

// document.getElementById("result").innerHTML += ' ' + c*2;

// function kk() {
//   var a = 1;
//   if (true) {
//     var b = 2;
//   }
//   c = a + b;

//   return c;
// }

// if (kk() === c) {
//   console.log("true");
// } else {
//   console.log("false");
// }

function MiClase(elId, elNombre) {
  var obj = this;
  obj.id = elId;
  obj.nombre = elNombre;
  obj.mostrar = function () {
    alert(obj.id + " " + obj.nombre);
  };
  obj.ponNombre = function (nuevoNombre) {
    obj.nombre = nom.toUpperCase();
  };
}

let obj1 = new MiClase(1, "uno");
obj1.mostrar();
fn = obj1.mostrar;
fn();
