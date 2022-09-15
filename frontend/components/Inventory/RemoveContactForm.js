import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import * as React from 'react';
import { useEffect, useState } from "react";


import Autocomplete from '@mui/material/Autocomplete';

export default function CreateSupplier() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
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
    fetch(`http://localhost:8787/${keyword}/contact/${inputId}`)
      .then((response) => response.json())
      .then((cData) => setData(cData._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchContactData();
  }, []);


  const deleteSupplier = () => {
    fetch(`http://localhost:8787/${keyword}/${inputId}`, { method: 'DELETE' })
    .then(async response => {
    })
  }

  return (
    <div>
      <div>
      <Autocomplete
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
          }}
          disablePortal
          id="combo-box-demo"
          options={sData}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Supplier" />
              <br />
            </div>
          )}
        />
        <br />
        <Autocomplete
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
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
       
      </div>
      <div>
        <Button
          color="error"
          sx={{ width: 300, marginTop: 4 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
          onClick={() => {
            deleteSupplier();
          }}
        >
          Delete Supplier
        </Button>
      </div>
    </div>
  );
}
