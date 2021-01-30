let model;

async function loadModel() {
	console.log("model loading..");
	loader = document.getElementById("progress-box");
	load_button = document.getElementById("load-button");
	loader.style.display = "block";
	modelName = "hand_recognition_net";
	model = undefined;
	model = await tf.loadLayersModel('./output/hand_recognition_net/model.json');
	loader.style.display = "none";
	load_button.disabled = true;
	load_button.innerHTML = "Loaded Model";
	console.log("model loaded..");
}

async function loadFile() {
	console.log("image is in loadfile..");
	document.getElementById("select-file-box").style.display = "table-cell";
  	document.getElementById("predict-box").style.display = "table-cell";
  	document.getElementById("prediction").innerHTML = "Click predict to find my label!";
  	var fileInputElement = document.getElementById("select-file-image");
  	console.log(fileInputElement.files[0]);
    renderImage(fileInputElement.files[0]);
}

function renderImage(file) {
  var reader = new FileReader();
  console.log("image is here..");
  reader.onload = function(event) {
    img_url = event.target.result;
    console.log("image is here2..");
    document.getElementById("test-image").src = img_url;
  }
  reader.readAsDataURL(file);
}


async function predButton() {
	console.log("model loading..");

	if (model == undefined) {
		alert("Please load the model first..")
	}
	if (document.getElementById("predict-box").style.display == "none") {
		alert("Please load an image using 'Demo Image' or 'Upload Image' button..")
	}
	console.log(model);
	let image  = document.getElementById("test-image");
	let tensor = preprocessImage(image, modelName);

	let predictions = await model.predict(tensor).data();
	console.log(predictions);
	let results = Array.from(predictions)
		.map(function (p, i) {
			return {
				probability: p,
				className: IMAGENET_CLASSES[i]
			};
		}).sort(function (a, b) {
			return b.probability - a.probability;
		}).slice(0, 5);

	document.getElementById("predict-box").style.display = "block";
	document.getElementById("prediction").innerHTML = "hand_recognition_Net prediction <br><b>" + results[0].className + "</b>";

	var ul = document.getElementById("predict-list");
	ul.innerHTML = "";
	results.forEach(function (p) {
		console.log(p.className + " " + p.probability.toFixed(6));
		var li = document.createElement("LI");
		li.innerHTML = p.className + " " + p.probability.toFixed(6);
		ul.appendChild(li);
	});

}
let width = 320
let height = 120
let num_Channel = 1
function preprocessImage(image, modelName) {
	let tensor = tf.browser.fromPixels(image, num_Channel)
		.resizeNearestNeighbor([height, width])
		.toFloat();
	console.log(tensor.shape);
	if (modelName === undefined) {
		return tensor.expandDims();
	} else if (modelName === "hand_recognition_net") {
		let offset = tf.scalar(127.5);
		return tensor.sub(offset)
			.div(offset)
			.expandDims();
	} else {
		alert("Unknown model name..")
	}
}

function loadDemoImage() {
	document.getElementById("predict-box").style.display = "table-cell";
  	document.getElementById("prediction").innerHTML = "Click predict to find my label!";
	document.getElementById("select-file-box").style.display = "table-cell";
	document.getElementById("predict-list").innerHTML = "";

	base_path = "dataset/test/test1.jpg"

	img_path = base_path
	document.getElementById("test-image").src = img_path;
}
