import * as React from 'react';
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import SendIcon from '@mui/icons-material/Send';

export default function UpdateSupplierForm() {
    const [inputValue, setInputValue] = React.useState('');
    const [inputId, setInputId] = React.useState('');
    const [keyword, setKeyword] = useState("supplierProcurement");
    const [data, setData] = useState([]);
    const fetchData = () => {
      fetch(`http://localhost:8787/${keyword}`)
        .then((response) => response.json())
        .then((data) => setData(data._embedded.supplierList))
        .catch((err) => console.error(err));
    };
  
    useEffect(() => {
      fetchData();
    }, []);  
  
    return (
      <div>
        <Autocomplete
          getOptionLabel={option => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ''));
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          
          sx={{ width: 400 }}
          renderInput={(params) => 
            <div>
              <TextField 
                {...params} 
                label="Suppliers"
              />
              <br />
              
            </div>
          }
        />
        <br />
        <TextField
            required
            id="outlined-required"
            label="New Company Name"
            name="companyName"
        />
        <br />
        <TextField
            margin="normal"
            required
            id="outlined-required"
            label="New Base Name"
            name="base"
        />
        <br />
        <Button 
            color='warning' 
            sx={{ width: 250, marginTop: 2 }} 
            type="submit" 
            variant="contained" 
            endIcon={<SendIcon />}>
            Update Supplier
        </Button>
      </div>
  );
}


