let loadBooksBtn = document.getElementById('loadBooks');

loadBooksBtn.addEventListener('click', onLoadBooks);

function onLoadBooks(event) {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    let authorsContainer = document.getElementById('authors-container')
    authorsContainer.innerHTML = ''

    fetch("http://localhost:8080/api/books", requestOptions)
        .then(response => response.json())
        .then(json => json.forEach(book => {
            //here we will create elements and add them to the table
            let row = document.createElement('tr')

            let titleCol = document.createElement('td')
            let authorCol = document.createElement('td')
            let isbnCol = document.createElement('td')
            let actionCol = document.createElement('td')

            //adding the columns to the parent row
            row.appendChild(titleCol)
            row.appendChild(authorCol)
            row.appendChild(isbnCol)
            row.appendChild(actionCol)

            titleCol.textContent = book.title
            authorCol.textContent = book.author.name
            isbnCol.textContent = book.isbn
            //actionCol.textContent = book.action

            authorsContainer.appendChild(row)
        }))
        .catch(error => console.log('error', error));
}