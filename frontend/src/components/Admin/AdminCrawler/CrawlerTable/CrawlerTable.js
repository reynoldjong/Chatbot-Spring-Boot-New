import React from 'react';
import CrawlerRow from './CrawlerRow/CrawlerRow';
/**
 * Compoenent representing a table of all the files in the  database
 * @param {props} props:
 *  @param {list} site - a list containing every crawled site in Chatbot.db
 *  @param {function} removeSiteHandler - function which removes a crawled site from Chatbot.db
 */
const crawlerTable = (props) => {

  return (
    <React.Fragment>
      <table className={" striped"}>
        <thead className={"z-depth-1"}>
          <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Remove</th>
          </tr>
        </thead>
        <tbody>
          {props.sites.map((item, i) => {
            return <CrawlerRow key={i} removeSiteHandler={(url) => props.removeSiteHandler(item['seed'])} name={item['seed']} date={item['date']}/>
          }

          )}

        </tbody>
      </table>

    </React.Fragment>
  );
}

export default crawlerTable;