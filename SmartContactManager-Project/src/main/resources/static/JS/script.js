  const baseUrl = "http://localhost:4041"
  // Ensure the script runs after the document is loaded
  document.addEventListener('DOMContentLoaded', function () {
      // Initialize the modal globally
      var modal1 = new bootstrap.Modal(document.getElementById('exampleModal'));

      // Function to show the modal
      function openContactModal() {
          modal1.show();
      }

      // Function to hide the modal
      function closeContactModal() {
          modal1.hide();
      }

      // Make loadContactdata globally available by attaching it to the window object
      window.loadContactdata = async function (id) {
          console.log("Fetching data for contact ID:", id);

          try {
              // Fetch contact data from the server
              const response = await fetch(`${baseUrl}/api/contacts/${id}`);
              
              // Ensure the fetch was successful
              if (!response.ok) {
                  throw new Error('Network response was not ok');
              }

              const data = await response.json();
              console.log("Contact data:", data);

              // Populate the modal with contact details
              document.querySelector("#contact_name").innerHTML = data.name;
              document.querySelector("#contact_email").innerHTML = data.email;
              document.querySelector("#contact_image").src = data.picture || 'default_image_url'; // Handle missing images
              document.querySelector("#contact_address").innerHTML = data.address;
              document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
              document.querySelector("#contact_about").innerHTML = data.description || 'No description available';

              // Handle favorite status
              const contactFavorite = document.querySelector("#contact_favorite");
              if (data.favorite) {
                  contactFavorite.innerHTML = "<i class='bi bi-heart-fill text-danger'></i>".repeat(1); // 5 stars
              } else {
                  contactFavorite.innerHTML = "Not Favorite Contact";
              }

              // Set website and LinkedIn links (if available)
              document.querySelector("#contact_website").href = data.websiteLink || '#';
              document.querySelector("#contact_website").innerHTML = data.websiteLink || 'No website available';
              document.querySelector("#contact_linkedIn").href = data.linkedInLink || '#';
              document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink || 'No LinkedIn available';

              // Open the modal after data is loaded
              openContactModal();
              
          } catch (error) {
              console.error("Error fetching contact data:", error);
              alert("Error loading contact details. Please try again later.");
          }
      };
  });
  
  window.deleteContact = async function (id){
	Swal.fire({
	  title: "Do you want to Delete the contact?",
	  icon: "warning",
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	 
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed) {
	    const url = `${baseUrl}/user/contacts/delete/${id}`;
		window.location.replace(url);
	  }
	});
  }

