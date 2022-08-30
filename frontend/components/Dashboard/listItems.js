import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import ListSubheader from "@mui/material/ListSubheader";
import DashboardIcon from "@mui/icons-material/Dashboard";
import PrecisionManufacturingIcon from '@mui/icons-material/PrecisionManufacturing';
import InventoryIcon from '@mui/icons-material/Inventory';
import BarChartIcon from "@mui/icons-material/BarChart";
import LoyaltyIcon from '@mui/icons-material/Loyalty';
import AssignmentIcon from "@mui/icons-material/Assignment";
import Link from "next/link";

export const mainListItems = (
  <React.Fragment>
    <Link href="/dash">
      <ListItemButton>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="Dashboard" />
      </ListItemButton>
    </Link>

    <Link href="/procurement">
      <ListItemButton>
        <ListItemIcon>
          <PrecisionManufacturingIcon />
        </ListItemIcon>
        <ListItemText primary="Procurement" />
      </ListItemButton>
    </Link>

    <Link href="/procurement">
      <ListItemButton>
        <ListItemIcon>
          <InventoryIcon />
        </ListItemIcon>
        <ListItemText primary="Inventory" />
      </ListItemButton>
    </Link>

    <Link href="/procurement">
      <ListItemButton>
        <ListItemIcon>
          <LoyaltyIcon />
        </ListItemIcon>
        <ListItemText primary="Sales" />
      </ListItemButton>
    </Link>

    <Link href="/procurement">
      <ListItemButton>
        <ListItemIcon>
          <BarChartIcon />
        </ListItemIcon>
        <ListItemText primary="Reports" />
      </ListItemButton>
    </Link>
  </React.Fragment>
);

export const secondaryListItems = (
  <React.Fragment>
    <ListSubheader component="div" inset>
      Saved reports
    </ListSubheader>
    <ListItemButton>
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText primary="Current month" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText primary="Last quarter" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText primary="Year-end sale" />
    </ListItemButton>
  </React.Fragment>
);
