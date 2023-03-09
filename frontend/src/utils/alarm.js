import swal from 'sweetalert2';

export const s1000 = {
  icon: 'success',
  // iconColor: 'rgba(211, 79, 4, 1)',
  showConfirmButton: false,
  timer: 1000,
  timerProgressBar: true,
};

export const s1500 = {
  icon: 'success',
  // iconColor: 'rgba(211, 79, 4, 1)',
  showConfirmButton: false,
  timer: 1500,
  timerProgressBar: true,
};

export const w1000 = {
  icon: 'warning',
  iconColor: 'red',
  showConfirmButton: false,
  timer: 1000,
  timerProgressBar: true,
};

export const w1500 = {
  icon: 'warning',
  iconColor: 'red',
  showConfirmButton: false,
  timer: 1500,
  timerProgressBar: true,
};

export const i1500 = {
  icon: 'info',
  // iconColor: 'blu',
  showConfirmButton: false,
  timer: 1500,
  timerProgressBar: true,
};

export const customAlert = (alertType, title, text = '') => {
  swal.fire({ ...alertType, title, text });
};
