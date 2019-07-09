import React from "react";

const adminControls = () => {
  return (
    <React.Fragment>
     
        <h3>Upload a File</h3>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </p>
        <form>
          
            <div className="input-field ">
              <input
                placeholder="Placeholder"
                id="first_name"
                type="text"
                className="validate"
              />
              <label for="first_name">First Name</label>
            </div>
        
           
        </form>
    
    </React.Fragment>
  );
};

export default adminControls;
