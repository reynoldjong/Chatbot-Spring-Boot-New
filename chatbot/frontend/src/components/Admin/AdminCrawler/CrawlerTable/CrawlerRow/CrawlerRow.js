import React from 'react';
/**
 * Component representing a row in a table with the name of the crawled site, the date
 * it was crawled on and the abillity to remove it
 * @param {props} props:
 *  @param {string} name - name of crawled site
 *  @param {string} date - date the site was crawled on
 *  @param {function} removeSiteHandler - function which removes a site from the Chatbot.db crawled sites schema
 */
const crawlerRow = (props) =>{
    return( 

          <tr>
            <td>{props.name}</td>
            <td>{props.date}</td>
            <td><button onClick={props.removeSiteHandler} className="btn red">Remove</button></td>
          </tr>

    );
};

export default crawlerRow;