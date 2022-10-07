import { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext({
  auth: null,
  setAuth: () => {},
  setRole: () => {},
  role: null,
});

export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(null);
  const [role, setRole] = useState(null);

  useEffect(() => {
    const isAuth = async () => {
      setRole(localStorage.getItem("userRole"))
    };

    isAuth();
  }, [auth]);

  return (
    <AuthContext.Provider value={{ auth, setAuth, setRole, role }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;