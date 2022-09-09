import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import * as React from 'react';
import { useEffect, useState } from "react";
import Alert from "@mui/material";


import Autocomplete from '@mui/material/Autocomplete';

export default function CreateSupplier() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);
  const [alert, setAlert] = React.useState(false);
  const [alertContent, setAlertContent] = React.useState('');
  const fetchData = () => {
    fetch(`http://localhost:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  const deleteSupplier = () => {
    fetch(`http://localhost:8787/${keyword}/${inputId}`, { method: 'DELETE'})
  }

  useEffect(() => {
    fetchData();
  }, []);

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
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Suppliers" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          id="filled-read-only-input"
          label="Supplier ID"
          value={inputId}
          defaultValue=""
          InputProps={{
            readOnly: true,
          }}
          variant="filled"
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
