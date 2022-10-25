import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import SendIcon from "@mui/icons-material/Send";
import { useEffect, useState } from "react";
import Autocomplete from '@mui/material/Autocomplete';
import { TextField } from "@mui/material";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function AlertDialog() {
  const [open, setOpen] = React.useState(false);
  const [warningOpen, setWarningOpen] = React.useState(false);

  const handleClickOpen = () => {
    if(inputValue != ""){
      setOpen(true);
    }
    else{
      setWarningOpen(true);
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  const warningClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setWarningOpen(false);
  };

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);

  const [buttonBool, setButtonValue] = useState('')

  const [supplier, setSupplier] = useState('');

  const fetchData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  const deleteSupplier = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${keyword}/${inputId}`, { method: 'DELETE' })
    .then(async response => {
      window.location.reload(false);
    })
  }

  useEffect(() => {
    setInterval(() => {
      fetchData();
    }, 1000);
  }, []);

  return (
    <div>
      <Autocomplete
          inputValue={supplier}
          disableClearable
          onChange={(e,v)=>setSupplier(`${v.companyName}: ${v.id}`)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
            setButtonValue("true");
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 470 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Suppliers" />
              <br />
            </div>
          )}
        />
      <Button
          color="error"
          sx={{ width: 300, marginTop: 4 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
          onClick={handleClickOpen}
        >Delete Supplier</Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Are you sure you want to delete the supplier?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            If you agree, the supplier selected and all of it's contacts will be removed, are you sure you wish to continue?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button 
          onClick={() => {
            deleteSupplier();
          }}
          autoFocus>
            I'm Sure, Delete Supplier
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar open={warningOpen} autoHideDuration={6000} onClose={warningClose}>
        <Alert onClose={warningClose} severity="warning" sx={{ width: '100%' }}>
          Please select a supplier to delete first!
        </Alert>
        </Snackbar>
    </div>
  );
}

