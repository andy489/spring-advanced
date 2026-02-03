// Delete
document.querySelector('#book-list').addEventListener('click', (e) => {
    target = e.target;

    if (target.classList.contains('delete')) {
        target.parentElement.parentElement.remove();

        deleteBtnClicked(e);

        showAlert('Book Data Deleted', 'danger');
    }
});

function deleteBtnClicked(e) {
    let bookId = e.target.dataset.id;

    deleteBook(bookId);
}

function deleteBook(bookId) {
    document.querySelector("#title").value = ''
    document.querySelector("#author").value = ''
    document.querySelector("#isbn").value = ''
    document.querySelector("#add-book-btn").value = 'Add'

    let requestOptions = {
        method: 'DELETE'
    };

    fetch(`http://localhost:8080/api/books/${bookId}`, requestOptions)
        .then(_ => {
        })
        .catch(error => console.log('error', error));

}