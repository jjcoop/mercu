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
    { field: "supplier_id", headerName: "Supplier ID", width: 100, minWidth: 100, maxWidth: 200 },
    { field: "id", headerName: "Contact ID", width: 100, minWidth: 100, maxWidth: 200 },
    { field: "name", headerName: "Name", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "phone", headerName: "Phone", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "email", headerName: "Email", width: 200, minWidth: 200, maxWidth: 300},
    { field: "position", headerName: "Position", width: 200, minWidth: 200, maxWidth: 300 },
  ];

  const rows = [];
  function createData(supplier_id, name, phone, email, position, id) {
    return { supplier_id, name, phone, email, position, id };
  }

  data.map((supplier) =>
    supplier.contacts.map((c) =>
      rows.push(
        createData(supplier.id, c.name, c.phone, c.email, c.position, c.id)
      )
    )
  );

  return (
    <div style={{ height: 400, width: "100%" }}>
      {/* <Title>Contacts</Title> */}
      <DataGrid
        sx={{
          "@media print": {
            ".MuiDataGrid-main": { color: "rgba(0, 0, 0, 0.87)" },
          },
        }}
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        // checkboxSelection
      />
    </div>
  );
}
