import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8000/api/acc/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'all');
  }

  getUserBoard() {
    return axios.get(API_URL + 'user', { headers: authHeader() });
  }

  getUserRoute(userId){
    return axios.get(API_URL + 'route/' + userId)
  }

  postUserRoute(userId, request, points, profile){
   return axios.post(API_URL + 'route/' + userId, {
      request,
      points,
      profile
    })
  }

  deleteUserRoute(routeId){
    return axios.delete(API_URL + 'route/' + routeId)
   }

  getModeratorBoard() {
    return axios.get(API_URL + 'mod', { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get(API_URL + 'admin', { headers: authHeader() });
  }
}

export default new UserService();
