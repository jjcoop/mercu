import * as React from "react";
import Link from "@mui/material/Link";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Title from "./Title";
import { useEffect, useState } from "react";

function preventDefault(event) {
  event.preventDefault();
}

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

  return (
    <React.Fragment>
      <Title>Procurement</Title>
      <Table size="small">
        <TableBody>
          {data.map((supplier, index) => (
            <TableRow key={index}>
              {supplier.contacts.map((c, i) => (
                <TableRow key={i}>
                  <TableCell>{c.id}</TableCell>
                  <TableCell>{c.name}</TableCell>
                  <TableCell>{c.phone}</TableCell>
                  <TableCell>{c.email}</TableCell>
                  <TableCell>{c.position}</TableCell>
                  <TableCell align="right"><Link href={`${c.supplier.href}`} >{`${c.supplier.href.split('/')[c.supplier.href.split("/").length - 1]}`}</Link></TableCell>
                </TableRow>
              ))}
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Link color="primary" href="#" onClick={preventDefault} sx={{ mt: 3 }}>
        See more suppliers
      </Link>
    </React.Fragment>
  );
}
