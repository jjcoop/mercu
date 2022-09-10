import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import SendIcon from '@mui/icons-material/Send';

export default function CreateSupplier() {
    



    return(
        <div>
            <div>
                <TextField
                    fullWidth
                    margin="normal"
                    required
                    id="outlined-required"
                    label="Required"
                    defaultValue="Company Name"
                />
            </div>
            
            <div>
                <TextField
                    fullWidth
                    margin="normal"
                    required
                    id="outlined-required"
                    label="Required"
                    defaultValue="Company Base"
                />
            </div>

            <div>
                <Button 
                    color='success' 
                    sx={{ width: 250, marginTop: 2 }} 
                    type="submit" 
                    variant="contained" 
                    endIcon={<SendIcon />}>
                    Create Supplier
                </Button>
            </div>
        </div>
        
    );
}
