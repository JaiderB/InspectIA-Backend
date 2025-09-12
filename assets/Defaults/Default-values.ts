import { state } from "assets/Typescript-generalities/Types";

export const messagesTitle = {
  'error': 'Error',
  'warning': 'Advertencia',
  'success': 'Éxito',
  'delete': 'Eliminar',
  'info': 'Información',
  'wrongCredentials': 'Credenciales incorrectas',
  'unableOption': "Opción no disponible",
  'emptyFields': "Campos vacíos",
  '': 'Modal generico'
}

export const messagesDescription = {
  'delete': '¿Estás seguro de que deseas eliminar el registro? No podrás deshacer tu acción después',
  'info': 'Es necesario que tu proveas el mensaje de información.',
  'warning': 'Esta operación no podrá ser revertiva. ¿Estás seguro de querer ejecutarla?',
  'error': 'Ha ocurrido un error, por favor vuelve a intentarlo de nuevo.',
  'success': 'La operación solicitada fue ejecutada con éxito.',
  'wrongCredentials': 'Las credenciales ingresadas no coinciden con los registros en sistema. Por favor, verifique su correo, contraseña y vuelva a intentar.',
  "unableOption": "Aún no contamos con esta opción disponible. Por favor comunicate con tu administrador.",
  'emptyFields': "Por favor diligencia los campos para poder continuar.",
  '': 'Esto es una modal normal'
}

export const statesDefect: state[] = [{ id: 1, name: 'Activo' }, { id: 2, name: 'Inactivo' }];

