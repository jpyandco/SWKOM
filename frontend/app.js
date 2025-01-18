document.getElementById("dataForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const form = document.getElementById("dataForm");
    if (!form) {
        console.error("The form element with ID 'dataForm' is not found in the DOM.");
        return;
    }
    else {
        console.info("Form found!");
    }
    console.info("Hello")

    const title = document.getElementById("title").value;
    const author = document.getElementById("author").value;
    const fileInput = document.getElementById("file");

    if (!fileInput.files[0]) {
        document.getElementById("responseMessage").textContent = "Please upload a PDF document.";
        return;
    }

    const formData = new FormData();
    formData.append("title", title);
    formData.append("author", author);
    formData.append("file", fileInput.files[0]);

    try {
        const response = await fetch("http://localhost:8081/api/document", {
            method: "POST",
            body: formData
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
                    <strong>Author:</strong> ${doc.author}
                    <strong>Document:</strong> 
                `;

                const downloadButton = document.createElement("button");
                downloadButton.textContent = "Download";
                downloadButton.onclick = () => downloadDocument(doc.id); // Trigger the download

                const deleteButton = document.createElement("button");
                deleteButton.textContent = "Delete";
                deleteButton.onclick = () => deleteDocument(doc.id);

                listItem.appendChild(content);
                listItem.appendChild(downloadButton);
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


async function downloadDocument(id) {
    try {
        const response = await fetch(`http://localhost:8081/api/document/${id}/download`);

        if (response.ok) {
            const blob = await response.blob();
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `document_${id}.pdf`; // Specify the file name
            link.click();
        } else {
            throw new Error("Failed to download document.");
        }
    } catch (error) {
        alert(`Error downloading document: ${error.message}`);
    }
}


document.addEventListener("DOMContentLoaded", fetchAllDocuments);
