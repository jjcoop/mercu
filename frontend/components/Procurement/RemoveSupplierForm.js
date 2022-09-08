import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import ContactsForm from "./ContactsForm";

export default function CreateSupplier() {
    return(
        <div>
            <div>
                <ContactsForm />
            </div>
            <div>
            <Button 
                  color='error' 
                  sx={{ width: 300, marginTop: 4 }} 
                  type="submit" 
                  variant="contained" 
                  endIcon={<SendIcon />}
                  onClick={() => {
                    alert('clicked');
                  }}>
                    Delete Supplier
                </Button>
            </div>
            
        </div>
        
    );
}
