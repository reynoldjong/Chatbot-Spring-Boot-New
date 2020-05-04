import axios from 'axios'
import jwt from "jsonwebtoken"

// use either session, local, or cookie
const storage = localStorage

export default class auth {

  static validateToken(token) {
    // decode token using secret key
    const decoded = jwt.decode(token, "secret")
    const currentTime = Date.now() / 1000; // to get in milliseconds

    // check if decoded token is valid or if session expired
    if (!decoded || (decoded && decoded.exp < currentTime)) {
      // remove axios header and clear session
      auth.clearSession()
      return null
    }

    // otherwise return payload
    return decoded
  }

  /** Get token payload (with encapsulated user session info)*/
  static getUser() {
    const token = auth.getToken()

    if (!token) {
      return null
    }
    return auth.validateToken(token)
  }

  static getToken() {
    return storage.token
  }

  /** set user sessions and http header as auth token */
  static setSession(token) {
    storage.setItem("token", token)
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
    console.log(axios.defaults.headers)
  }

  /** Clear authentication token from session */
  static clearSession() {
    storage.removeItem("token")
    delete axios.defaults.headers.common['Authorization'];
    console.log(axios.defaults.headers)
  }

}