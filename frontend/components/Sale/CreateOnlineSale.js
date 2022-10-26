import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";

import Box from "@mui/material/Box";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import parse from "autosuggest-highlight/parse";
import throttle from "lodash/throttle";

const GOOGLE_MAPS_API_KEY = "AIzaSyAE-jxMqVoEB-SSDEUHcG79O2zh8Sy5ZiU";

function loadScript(src, position, id) {
  if (!position) {
    return;
  }

  const script = document.createElement("script");
  script.setAttribute("async", "");
  script.setAttribute("id", id);
  script.src = src;
  position.appendChild(script);
}

const autocompleteService = { current: null };

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function CreateOnlineSale() {
  const [value, setValue] = React.useState(null);
  const [iv, setIV] = React.useState("");
  const [options, setOptions] = React.useState([]);
  const loaded = React.useRef(false);

  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const [pData, setProductData] = useState([]);

  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);

  const [customerAddress, setCustomerAddress] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [quantity, setQuantity] = useState("");
  const [test, setTest] = useState("");
  const [resetBool, setReset] = React.useState(false);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };

  const fetchProductData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    setInterval(() => {
      fetchProductData();
    }, 1000);
  }, []);

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      customerName: event.target.customerName.value,
      address: iv.toString(),
      productName: productValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8789/${keyword}/online`;

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

    if (response.status == 201) {
      setOpen(true);
      setCustomerName("");
      setCustomerAddress("");
      setQuantity("");
      setTest("");
      setReset(true);
      setValue("");
    } else {
      setBadOpen(true);
    }
  };

  if (typeof window !== "undefined" && !loaded.current) {
    if (!document.querySelector("#google-maps")) {
      loadScript(
        `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}&libraries=places&callback=initMap`,
        document.querySelector("head"),
        "google-maps"
      );
    }

    loaded.current = true;
  }

  const gFetch = React.useMemo(
    () =>
      throttle((request, callback) => {
        autocompleteService.current.getPlacePredictions(request, callback);
      }, 200),
    []
  );

  React.useEffect(() => {
    let active = true;

    if (!autocompleteService.current && window.google) {
      autocompleteService.current =
        new window.google.maps.places.AutocompleteService();
    }
    if (!autocompleteService.current) {
      return undefined;
    }

    if (iv === "") {
      setOptions(value ? [value] : []);
      return undefined;
    }

    gFetch({ input: iv }, (results) => {
      if (active) {
        let newOptions = [];

        if (value) {
          newOptions = [value];
        }

        if (results) {
          newOptions = [...newOptions, ...results];
        }

        setOptions(newOptions);
      }
    });

    return () => {
      active = false;
    };
  }, [value, iv, gFetch]);

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Customer Name"
          name="customerName"
          onChange={(event) => setCustomerName(event.target.value)}
          value={customerName}
        />
        <br />
        <br />
        <Autocomplete
          id="google-map-demo"
          sx={{ width: "75%" }}
          getOptionLabel={(option) =>
            typeof option === "string" ? option : option.description
          }
          filterOptions={(x) => x}
          options={options}
          autoComplete
          includeInputInList
          filterSelectedOptions
          value={value}
          onChange={(event, newValue) => {
            setOptions(newValue ? [newValue, ...options] : options);
            setValue(newValue);
          }}
          onInputChange={(event, newInputValue) => {
            setIV(newInputValue);
          }}
          renderInput={(params) => (
            <TextField 
            key={resetBool}
            {...params} 
            label="Customer Address" 
            fullWidth />
          )}
          renderOption={(props, option) => {
            const matches =
              option.structured_formatting.main_text_matched_substrings;
            const parts = parse(
              option.structured_formatting.main_text,
              matches.map((match) => [
                match.offset,
                match.offset + match.length,
              ])
            );

            return (
              <li {...props}>
                <Grid container alignItems="center">
                  <Grid item>
                    <Box
                      component={LocationOnIcon}
                      sx={{ color: "text.secondary", mr: 2 }}
                    />
                  </Grid>
                  <Grid item xs>
                    {parts.map((part, index) => (
                      <span
                        key={index}
                        style={{
                          fontWeight: part.highlight ? 700 : 400,
                        }}
                      >
                        {part.text}
                      </span>
                    ))}

                    <Typography variant="body2" color="text.secondary">
                      {option.structured_formatting.secondary_text}
                    </Typography>
                  </Grid>
                </Grid>
              </li>
            );
          }}
        />

        {/* <TextField
          fullWidth
          required
          id="outlined-required"
          label="Customer Address"
          name="customerAddress"
          onChange={event => setCustomerAddress(event.target.value)}
          value={customerAddress}
        /> */}
        <br />
        <Autocomplete
          //disableClearable
          inputValue={test}
          key={resetBool}
          //onChange={(e,v)=>setTest(v?.name||v)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(x) => `${x.name}: ${x.id}`}
          onInputChange={(event, newproductValue) => {
            setTest(newproductValue);
            setProductValue(
              newproductValue.substring(0, newproductValue.indexOf(":"))
            );
          }}
          disablePortal
          id="combo-box-demo"
          options={pData}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Product" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Quantity"
          name="quantity"
          onChange={(event) => setQuantity(event.target.value)}
          value={quantity}
        />
        <br />
        <Button
          color="success"
          sx={{ width: "75%", marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Online Sale
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert
            onClose={handleClose}
            severity="success"
            sx={{ width: "100%" }}
          >
            Success! New Online Sale Created
          </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="error" sx={{ width: "100%" }}>
            Fail! Couldn't create new online sale!
          </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
