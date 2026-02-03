let selectedRow = null;

let reloadBtn = document.getElementById('reloadBooks');
reloadBtn.addEventListener('click', reloadBooks);

function reloadBooks() {

    document.querySelector('#title').value = ''
    document.querySelector('#author').value = ''
    document.querySelector('#isbn').value = ''
    document.querySelector('#add-book-btn').value = 'Add'

    let booksContainer = document.getElementById('book-list');
    booksContainer.innerHTML = '';

    fetch('http://localhost:8080/api/books')
        .then(response => response.json())
        .then(json => json.forEach(book => {

                    // create row
                    let row = document.createElement('tr');

                    let titleCol = document.createElement('td');
                    let authorCol = document.createElement('td');
                    let isbnCol = document.createElement('td');
                    let actionCol = document.createElement('td');

                    // fill book
                    titleCol.textContent = book.title;
                    authorCol.textContent = book.author.name;
                    isbnCol.textContent = book.isbn;

                    // actions

                    // update-btn
                    let updateBtn = document.createElement('button');
                    updateBtn.classList.add('btn', 'btn-warning', 'btn-sm', 'edit', 'font-weight-bold', 'mr-3');
                    updateBtn.innerText = "Edit";
                    updateBtn.dataset.id = book.id;
                    actionCol.appendChild(updateBtn);

                    // delete-btn
                    let deleteBtn = document.createElement('button');
                    deleteBtn.classList.add('btn', 'btn-danger', 'btn-sm', 'delete', 'font-weight-bold');
                    deleteBtn.innerText = 'Delete';
                    deleteBtn.dataset.id = book.id;
                    actionCol.appendChild(deleteBtn);

                    // fill and add row
                    row.appendChild(titleCol)
                    row.appendChild(authorCol)
                    row.appendChild(isbnCol)
                    row.appendChild(actionCol)

                    booksContainer.appendChild(row)
                }
            )
        );
}