import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

export default function UpdateContactForm() {
  const [inputValue, setInputValue] = useState("");
  const [inputId, setInputId] = useState("");
  const [contactID, setContactID] = useState("");
  const [contactName, setContactName] = useState("");
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [sData, setSupplierData] = useState([]);
  const [cData, setContactData] = useState([]);


  const fetchSupplierData = () => {
    fetch(`http://localhost:8787/${keyword}`)
      .then((response) => response.json())
      .then((sData) => setSupplierData(sData._embedded.supplierList))
      .catch((err) => console.error(err));
    
  };

  useEffect(() => {
    fetchSupplierData();
  }, []);

  const fetchContactData = () => {
    fetch(`http://localhost:8787/${keyword}/${inputId}`)
      .then((response) => response.json())
      .then((cData) => setContactData(cData.contacts))
      .catch((err) => console.error(err));
  };

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8787/supplierProcurement/${inputId}/contact/${contactID}`;

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "DELETE",
    };

    // Send the form data to our forms API on Vercel and get a response.
    const response = await fetch(endpoint, options);

    // Get the response data from server as JSON.
    // If server returns the name submitted, that means the form works.
    const result = await response.json();

    if (response.status == 201) {
      alert(
        "Deleted Contact: " +
          contactName +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <Autocomplete
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue)
            setInputId(newInputValue.replace(/\D/g, ""))
          }}
          //onMouseOut is crucial to the fetching of contact data
          //don't delete this line
          onMouseOut={fetchContactData()}
          disablePortal
          id="combo-box-demo"
          options={sData}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} 
              label="Supplier" />
              <br />
            </div>
          )}
        />
        <br />
        <Autocomplete
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.name}: ${option.id}`}
          onInputChange={(event, value) => {
            setContactName(value)
            setContactID(value.replace(/\D/g, ""))
          }}
          disablePortal
          id="combo-box-demo"
          options={cData}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Contact" />
              <br />
            </div>
          )}
        />
        <br />
        <Button
          color="error"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Remove Contact
        </Button>
      </form>
    </div>
  );
}