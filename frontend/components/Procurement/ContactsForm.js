import * as React from 'react';
import { useEffect, useState } from "react";

import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';

export default function ContactsForm() {
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
    <Autocomplete
      disablePortal
      id="combo-box-demo"
      options={data}
      sx={{ width: 300 }}
      renderInput={(params) => <TextField {...params} label="Suppliers" />}
      getOptionLabel={option => option.companyName}
    />
  );
}
