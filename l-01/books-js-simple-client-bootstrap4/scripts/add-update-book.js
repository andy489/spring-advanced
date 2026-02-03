// Add Book
document.querySelector("#book-form").addEventListener("submit", (e) => {
    e.preventDefault();

    // Get Form Values
    const title = document.querySelector("#title").value;
    const author = document.querySelector("#author").value;
    const isbn = document.querySelector("#isbn").value;

    // console.log('title: ' + title)
    // console.log('author: ' + author)
    // console.log('isbn: ' + isbn)

    // validate
    if (title === "" || author === "" || isbn === "") {
        showAlert("Please fill in all fields", "danger");
    } else {
        let bookBody = {
            title: title,
            author: {
                name: author
            },
            isbn: isbn
        };

        if (selectedRow == null) {
            addUpdateBook(bookBody);
            showAlert("Book Added", "success");
        } else {
            let bookId = e.target.dataset.id;
            delete e.target.dataset;
            selectedRow = null;

            updateBook(bookId, bookBody);

            showAlert("Book Info Edited", "info");
        }

        clearFields();
    }
});

function addUpdateBook(bookBody) {
    let requestOptions = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(bookBody)
    }

    // console.log('requestOptions: ' + requestOptions.body)

    fetch('http://localhost:8080/api/books', requestOptions)
        .then(_ => reloadBooks())
        .catch(error => console.log('error', error));
}


// Edit data (Update)
document.querySelector("#book-list").addEventListener("click", (e) => {
    target = e.target;

    if (target.classList.contains('edit')) {
        let bookId = target.dataset.id;

        selectedRow = target.parentElement.parentElement;
        document.querySelector("#add-book-btn").value = 'Update'

        let title = selectedRow.children[0].textContent;
        let author = selectedRow.children[1].textContent;
        let isbn = selectedRow.children[2].textContent;

        document.querySelector('#title').value = title;
        document.querySelector('#author').value = author;
        document.querySelector('#isbn').value = isbn;

        document.querySelector('#book-form').dataset.id = bookId;
    }
});

function updateBook(bookId, bookBody) {

    let requestOptions = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        method: 'PUT',
        body: JSON.stringify(bookBody)
    }

    fetch(`http://localhost:8080/api/books/${bookId}`, requestOptions)
        .then(_ => reloadBooks())
        .catch(error => console.log('error', error));
}

// Clear All Fields
function clearFields() {
    document.querySelector("#title").value = '';
    document.querySelector("#author").value = '';
    document.querySelector("#isbn").value = '';
}