import React, { useEffect, useState, useRef, useCallback } from "react";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";
import Button from "@mui/material/Button";
import { DataGrid } from "@mui/x-data-grid";

export default function CreateSupplierForm() {
  const [isSending, setIsSending] = useState(false)
  const isMounted = useRef(true)

  useEffect(() => {
    return () => {
      fetchData()
      isMounted.current = false
    }
  }, [])

  const sendRequest = useCallback(async () => {
    // don't send again while we are sending
    if (isSending) return
    // update state
    setIsSending(true)
    // send the actual request
    await API.sendRequest()
    // once the request is sent, update state again
    if (isMounted.current) // only update if we are still mounted
      setIsSending(false)
  }, [isSending]) // update the callback if the state changes


  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  //useEffect(() => {
    //fetchData();
  //}, []);

  const columns = [
    { field: "id", headerName: "ID", width: 75, minWidth: 75, maxWidth: 200 },
    { field: "companyName", headerName: "Company Name", width: 250, minWidth: 200, maxWidth: 300},
    { field: "base", headerName: "State", width: 125, minWidth: 150, maxWidth: 200},
    { field: "contacts", headerName: "Contacts #", width: 100, minWidth: 75, maxWidth: 200},
  ];

  const rows = [];
  function createData(id, companyName, base, contacts) {
    return {id, companyName, base, contacts};
  }

  data.map((supplier) =>
      rows.push(
        createData(supplier.id, supplier.companyName, supplier.base, supplier.contacts.length)
      )
  );




  const [myValue, setValue] = useState("");

  // Handles the submit event on form submit.
  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      companyName: event.target.companyName.value,
      base: event.target.base.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // API endpoint where we send form data.
    const endpoint = "http://localhost:8787/supplierProcurement";

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "POST",
      // Tell the server we're sending JSON.
      headers: {
        "Content-Type": "application/json",
      },
      // Body of the request is the JSON data we created above.
      body: JSONdata,
    };

    // Send the form data to our forms API on Vercel and get a response.
    const response = await fetch(endpoint, options);

    // Get the response data from server as JSON.
    // If server returns the name submitted, that means the form works.
    const result = await response.json();

    if(response.status == 201){
      //useEffect();
      //alert("Created New Supplier: " + data.companyName + ". Refreshing webpage now...")
      //window.location.reload(false)
    }
  };
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Company Name"
          name="companyName"
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Base Name"
          name="base"
        />
        <br />
        <br />
        <Button color="success" type="submit" onClick={fetchData()} variant="contained" endIcon={<SendIcon />}>
          Create Supplier
        </Button>
      </form>
      <br />
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        // checkboxSelection
      />
    </div>
    
  );
}
