import * as React from 'react';
import { useEffect, useState } from "react";

import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';

export default function ContactsForm() {
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
  );
}
