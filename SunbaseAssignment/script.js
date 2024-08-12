let currentPage = 1; // Initialize current page number
let sortBy = document.getElementById('searchBy').value; // Initialize sortBy with the default value
let isEditing = false; // Flag to determine if the form is in edit/add mode

document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault(); //Not allowing form to submit normally

    const email = document.getElementById('userid').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/api/v1/auth/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json(); // Parse the response JSON
            localStorage.setItem('jwtToken', data.jwtToken); // Store the token
            document.getElementById('loginpage').style.display = 'none';
            document.getElementById('mainpage').style.display = 'block';
            await fetchAndDisplayCustomers(currentPage); // Fetch and display customer data
        } else {
            // Show error message
            document.getElementById('loginError').style.display = 'block';
        }
    } catch (error) {
        alert("Error: Unable to connect to the server.");
    }
});

async function fetchAndDisplayCustomers(pageNo) {
    const fetchAllReqDto = {
        pageNo: pageNo,
        pageSize: 5, // Number of items per page
        sortBy: sortBy // Use the current value of sortBy
    };

    try {
        const response = await fetch('http://localhost:8080/api/v1/customer/fetchAll', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            body: JSON.stringify(fetchAllReqDto)
        });

        if (response.ok) {
            const data = await response.json();
            const customers = data.result; // Extract the customer data from the response

            // Get the table body element
            const tableBody = document.getElementById('customerTableBody');

            // clear data from existing rows
            tableBody.innerHTML = '';

            // Update table rows with customer data
            customers.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.address}</td>
                    <td>${customer.city}</td>
                    <td>${customer.state}</td>
                    <td>${customer.email}</td>
                    <td>${customer.phone}</td>
                    <td>
                        <button class="edit-btn" data-customer='${JSON.stringify(customer)}'>Edit</button>
                        <button class="delete-btn">Delete</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            // adding event listener to edit button
            document.querySelectorAll('.edit-btn').forEach(button => {
                button.addEventListener('click', function() {
                    const customer = JSON.parse(this.getAttribute('data-customer'));
                    loadCustomerDataForEdit(customer);
                });
            });

            // adding event listener to delete button
            document.querySelectorAll('.delete-btn').forEach(button => {
                button.addEventListener('click', async function() {
                    const row = this.closest('tr');

                    // getting phone number from the row
                    const phone = row.querySelector('td:nth-child(7)').textContent;

                    if (confirm('Are you sure you want to delete this customer?')) {
                        try {
                            const deleteResponse = await fetch(`http://localhost:8080/api/v1/customer/delete?phone=${encodeURIComponent(phone)}`, {
                                method: 'DELETE',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                                }
                            });

                            if (deleteResponse.ok) {
                                // Removing the row from the table
                                row.remove();
                                alert('Customer deleted successfully.');
                            } else {
                                alert('Failed to delete customer.');
                            }
                        } catch (error) {
                            alert('Error: Unable to connect to the server.');
                        }
                    }
                });
            });
        } else {
            alert("Failed to fetch customer data.");
        }
    } catch (error) {
        alert("Error: Unable to connect to the server.");
    }
}

document.getElementById('nextButton').addEventListener('click', function() {
    currentPage++;
    fetchAndDisplayCustomers(currentPage);
});

document.getElementById('prevButton').addEventListener('click', function() {
    if (currentPage > 1) {
        currentPage--;
        fetchAndDisplayCustomers(currentPage);
    }
});

document.getElementById('searchBy').addEventListener('change', function() {
    sortBy = this.value; // Updating sortBy with the selected value
    fetchAndDisplayCustomers(currentPage); // Fetch customers with the new sortBy value
});

document.getElementById('addCustomerButton').addEventListener('click', function() {
    document.getElementById('mainpage').style.display = 'none';
    document.getElementById('customerFormPage').style.display = 'block';
    document.getElementById('formTitle').textContent = 'Add Customer';
    document.getElementById('customerForm').reset(); // Reset the form fields
});

document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('customerFormPage').style.display = 'none';
    document.getElementById('mainpage').style.display = 'block';
});

document.getElementById('customerForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Not allowing form to submit normally

    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => { data[key] = value; });

    try {
        const url = document.getElementById('formTitle').textContent === 'Add Customer'
            ? 'http://localhost:8080/api/v1/customer/add'
            : 'http://localhost:8080/api/v1/customer/update'; 

        const response = await fetch(url, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Customer saved successfully.');
        } else {
            alert('Failed to save customer.');
        }

        document.getElementById('customerFormPage').style.display = 'none';
        document.getElementById('mainpage').style.display = 'block';
        // Optionally, refresh the customer list here
        await fetchAndDisplayCustomers(currentPage);
    } catch (error) {
        alert('Error: Unable to connect to the server.');
    }
});

function loadCustomerDataForEdit(customer) {
    document.getElementById('mainpage').style.display = 'none';
    document.getElementById('customerFormPage').style.display = 'block';
    document.getElementById('formTitle').textContent = 'Edit Customer';
    document.getElementById('firstName').value = customer.firstName;
    document.getElementById('lastName').value = customer.lastName;
    document.getElementById('address').value = customer.address;
    document.getElementById('city').value = customer.city;
    document.getElementById('state').value = customer.state;
    document.getElementById('email').value = customer.email;
    document.getElementById('phone').value = customer.phone;
}

document.getElementById('syncButton').addEventListener('click', async function() {
    try {
        const response = await fetch('http://localhost:8080/api/v1/customer/sync', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            }
        });

        if (response.ok) {
            alert('Data synchronized successfully.');
            // Refresh the customer list
            await fetchAndDisplayCustomers(currentPage);
        } else {
            alert('Failed to synchronize data.');
        }
    } catch (error) {
        alert('Error: Unable to connect to the server.');
    }
});