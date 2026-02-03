// alert('Test JS');

(function () {
    dynamicallyLoadScript('./../scripts/show-alert.js');
    dynamicallyLoadScript('./../scripts/delete-book.js');
    dynamicallyLoadScript('./../scripts/add-update-book.js');
    dynamicallyLoadScript('./../scripts/display-books.js');
})();

function dynamicallyLoadScript(url) {
    let script = document.createElement('script');  // create a script DOM node
    script.src = url;  // set its src to the provided URL

    document.body.appendChild(script); // add it to the end of the body section of the page
}

window.onload = function () {
    reloadBooks();
};

