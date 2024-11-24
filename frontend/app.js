document.getElementById("dataForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const title = document.getElementById("title").value;
    const author = document.getElementById("author").value;
    const text = document.getElementById("text").value;

    try {
        const response = await fetch("http://localhost:8081/api/document", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({title, author, text}),
        });

        if (response.ok) {
            document.getElementById("responseMessage").textContent = "Document saved successfully!";
            await fetchAllDocuments();
        } else {
            throw new Error(`Failed to save document.`);
        }
    } catch (error) {
        document.getElementById("responseMessage").textContent = `Error: ${error.message}`;
    }
});

async function deleteDocument(id) {
    try {
        const response = await fetch(`http://localhost:8081/api/document/${id}`, {method: "DELETE"});

        if (response.ok) {
            alert("Document deleted successfully!");
            await fetchAllDocuments();
        } else {
            throw new Error(`Failed to delete document.`);
        }
    } catch (error) {
        alert(`Error deleting document: ${error.message}`);
    }
}

async function fetchAllDocuments() {
    try {
        const response = await fetch("http://localhost:8081/api/all");

        if (response.ok) {
            const documents = await response.json();
            const dataList = document.getElementById("dataList");
            dataList.innerHTML = "";

            documents.forEach(doc => {
                const listItem = document.createElement("li");

                const content = document.createElement("span");
                content.innerHTML = `
                    <strong>Title:</strong> ${doc.title}, 
                    <strong>Author:</strong> ${doc.author}, 
                    <strong>Text:</strong> ${doc.text}
                `;

                const deleteButton = document.createElement("button");
                deleteButton.textContent = "Delete";
                deleteButton.onclick = () => deleteDocument(doc.id);

                listItem.appendChild(content);
                listItem.appendChild(deleteButton);

                dataList.appendChild(listItem);
            });
        } else {
            throw new Error(`Failed to fetch documents.`);
        }
    } catch (error) {
        alert(`Error fetching documents: ${error.message}`);
    }
}


document.addEventListener("DOMContentLoaded", fetchAllDocuments);
