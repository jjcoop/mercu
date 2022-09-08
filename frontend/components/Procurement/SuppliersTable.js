import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Title from "./Title";


export default function contacts() {
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

  const columns = [
    { field: "id", headerName: "ID", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "companyName", headerName: "Company Name", width: 125, minWidth: 150, maxWidth: 200},
    { field: "base", headerName: "Administrative Unit", width: 125, minWidth: 150, maxWidth: 200},
    {
      field: "contacts",
      headerName: "Contacts #",
      type: 'number',
      width: 125, minWidth: 150, maxWidth: 200
    },
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

  return (
    <div style={{ height: 400, width: "100%" }}>
      {/* <Title>Suppliers</Title> */}
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
