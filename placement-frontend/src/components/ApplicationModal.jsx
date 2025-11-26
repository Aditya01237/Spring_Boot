import { useState } from 'react';
import api from '../api/axiosConfig';

const ApplicationModal = ({ placement, onClose }) => {
  const [aboutText, setAboutText] = useState('');
  const [cvFile, setCvFile] = useState(null);
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  // Handler for file selection
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      // Validate file type and size
      if (file.type === 'application/pdf') {
        if (file.size <= 5 * 1024 * 1024) { // 5MB limit
          setCvFile(file);
          setError('');
        } else {
          alert("File size must be less than 5MB");
          e.target.value = null;
          setCvFile(null);
        }
      } else {
        alert("Please upload a file in PDF format (.pdf)");
        e.target.value = null;
        setCvFile(null);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    // Validation
    if (!cvFile) {
      setError('Please upload your CV (PDF required).');
      setSubmitting(false);
      return;
    }

    if (!aboutText.trim()) {
      setError('Please provide a brief statement of interest.');
      setSubmitting(false);
      return;
    }

    // Prepare FormData
    const formData = new FormData();
    formData.append('cvFile', cvFile);
    formData.append('about', aboutText.trim());

    try {
      // Make sure placementId is sent correctly
      await api.post(`/applications/apply/${placement.id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      alert('Application and CV submitted successfully!');
      onClose();
    } catch (err) {
      console.error('Application submission error:', err);
      const errorMessage = err.response?.data?.error || 
                          err.response?.data?.message || 
                          "Application failed. You may have already applied.";
      setError(errorMessage);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-gray-900 bg-opacity-70 flex justify-center items-center z-50 p-4">
      <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <h2 className="text-2xl font-bold mb-4 text-blue-700 border-b pb-2">
          Apply for {placement.profile}
        </h2>
        <p className="text-sm text-gray-600 mb-4">
          {placement.organisation}
        </p>
        
        <form onSubmit={handleSubmit} className="space-y-5">
          
          {/* Statement of Interest Input */}
          <div>
            <label htmlFor="about" className="block text-sm font-medium text-gray-700 mb-1">
              Statement of Interest <span className="text-red-500">*</span>
            </label>
            <textarea
              id="about"
              rows="4"
              value={aboutText}
              onChange={(e) => setAboutText(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition"
              placeholder="Why are you a good fit for this role?"
              required
            ></textarea>
            <p className="text-xs text-gray-500 mt-1">
              {aboutText.length} characters
            </p>
          </div>
          
          {/* CV File Upload Input */}
          <div>
            <label htmlFor="cvFile" className="block text-sm font-medium text-gray-700 mb-1">
              Upload CV <span className="text-red-500">*</span>
            </label>
            <div className="flex items-center justify-center w-full">
              <label htmlFor="cvFile" className="flex flex-col items-center justify-center w-full h-32 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-blue-50 hover:border-blue-400 transition">
                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg className="w-8 h-8 mb-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                  </svg>
                  <p className="mb-2 text-sm text-gray-500">
                    <span className="font-semibold">Click to upload</span> or drag and drop
                  </p>
                  <p className="text-xs text-gray-500">PDF (MAX. 5MB)</p>
                </div>
                <input 
                  id="cvFile" 
                  type="file" 
                  accept=".pdf" 
                  className="hidden" 
                  onChange={handleFileChange}
                  required
                />
              </label>
            </div> 
            {cvFile && (
              <div className="mt-2 flex items-center text-sm text-green-600 bg-green-50 p-3 rounded-lg border border-green-200">
                <svg className="w-5 h-5 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd"></path>
                </svg>
                <span className="flex-1 truncate">
                  Selected: <span className="font-semibold">{cvFile.name}</span>
                </span>
                <button
                  type="button"
                  onClick={() => {
                    setCvFile(null);
                    document.getElementById('cvFile').value = null;
                  }}
                  className="ml-2 text-red-500 hover:text-red-700"
                >
                  ✕
                </button>
              </div>
            )}
          </div>

          {error && (
            <div className="text-red-600 text-sm text-center font-medium bg-red-50 p-3 rounded-lg border border-red-200">
              {error}
            </div>
          )}

          <div className="flex justify-end space-x-3 pt-4 border-t border-gray-100">
            <button
              type="button"
              onClick={onClose}
              className="px-5 py-2 text-gray-700 bg-gray-200 rounded-lg hover:bg-gray-300 transition font-medium"
              disabled={submitting}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed font-semibold shadow-md"
              disabled={submitting}
            >
              {submitting ? 'Submitting...' : 'Submit Application'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ApplicationModal;