import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";

import Box from "@mui/material/Box";
import Autocomplete from "@mui/material/Autocomplete";
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

const autocompleteService = { 
  current: null 
};

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function CreateStoreForm() {
  const [value, setValue] = React.useState(null);
  const [iv, setIV] = React.useState("");
  const [options, setOptions] = React.useState([]);
  const loaded = React.useRef(false);

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [data, setData] = useState([]);

  const [storeAddress, setStoreAddress] = useState("");
  const [managerName, setManagerName] = useState("");

  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      address: storeAddress,
      managerName: event.target.storeManager.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // API endpoint where we send form data.

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8789/${keyword}/store`;

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
      setStoreAddress("");
      setManagerName("");
      setValue("");
    } else {
      setBadOpen(true);
    }
  };

  if (typeof window !== "undefined" && !loaded.current) {
    if (!document.querySelector("#google-maps")) {
      loadScript(
        `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}&region=au&libraries=places`,
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
        <Autocomplete
          
          id="google-map-demo"
          sx={{ width: 500 }}
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
            setStoreAddress(newInputValue)
          }}
          renderInput={(params) => (
            <TextField {...params} label="Add a location" fullWidth />
          )}
          
        />
        {/* //if this component no longer works the google cloud API might have run outlined
        //if so, remove the google API autocomplete and replace with this text field */}
        {/* <TextField
          required
          fullWidth
          id="outlined-required"
          label="Store Address"
          name="storeAddress"
          onChange={event => setStoreAddress(event.target.value)}
          value={storeAddress}
        /> */}
        <br />
        <TextField
          required
          sx={{ width: 500 }}
          id="outlined-required"
          label="Store Manager Name"
          name="storeManager"
          onChange={(event) => setManagerName(event.target.value)}
          value={managerName}
        />
        <br />
        <Button
          color="success"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Store
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert
            onClose={handleClose}
            severity="success"
            sx={{ width: "100%" }}
          >
            Success! New Store Created
          </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="error" sx={{ width: "100%" }}>
            Fail! Couldn't create new store!
          </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
